package com.okcoin.house.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.okcoin.house.api.domain.House;
import com.okcoin.house.api.domain.HouseUser;
import com.okcoin.house.api.domain.User;
import com.okcoin.house.api.service.HouseService;
import com.okcoin.house.api.service.UserService;
import com.okcoin.house.common.autoconfig.OssProperties;
import com.okcoin.house.common.support.enums.FileType;
import com.okcoin.house.common.support.enums.HouseSortEnum;
import com.okcoin.house.common.support.enums.HouseUserType;
import com.okcoin.house.common.support.enums.NoticeType;
import com.okcoin.house.common.support.model.Pager;
import com.okcoin.house.common.util.ProvinceEnum;
import com.okcoin.house.dao.main.HouseMapper;
import com.okcoin.house.dao.main.HouseMsgMapper;
import com.okcoin.house.dto.HouseDto;
import com.okcoin.house.dto.UserMsg;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: liupeng
 * @date: 2019/4/19 11:04
 * @description(功能描述):
 */
@Service
public class HouseServiceImpl implements HouseService {

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private OssProperties ossProperties;

    @Autowired
    private HouseUserServiceImpl houseUserService;

    @Autowired
    private NoticService noticService;

    @Autowired
    private UserService userService;

    @Autowired
    private HouseMsgMapper houseMsgMapper;

    @Autowired
    private RecommendService recommendService;

    @Override
    public List<HouseDto> getLateHouse() {
        List<HouseDto> collect = houseMapper.getLateHouse().stream()
                .map(x -> HouseDto.builder()
                        .id(x.getId())
                        .address(x.getAddress())
                        .area(x.getArea())
                        .baths(x.getBaths())
                        .beds(x.getBeds())
                        .firstImg(x.getImage().split(",")[0])
                        .name(x.getName())
                        .price(x.getPrice())
                        .build())
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public Pager<HouseDto> getHouseListByQuery(Integer pageSize, Integer pageNum, String name, Integer type, String sorting) {
        HouseSortEnum byValue = HouseSortEnum.getByType(sorting);
        PageInfo<House> re = PageHelper.startPage(pageNum, pageSize, byValue.getSort()).doSelectPageInfo(() -> houseMapper.selectByNameType(name, type));
        List<House> list = re.getList();
        List<HouseDto> dtos = list.stream().map(x -> HouseDto.builder()
                .id(x.getId())
                .address(x.getAddress())
                .area(x.getArea())
                .baths(x.getBaths())
                .beds(x.getBeds())
                .firstImg(x.getImage().split(",")[0])
                .name(x.getName())
                .price(x.getPrice())
                .typeStr(x.getType() ? "直售" : "出租")
                .remarks(x.getRemarks())
                .build()).collect(Collectors.toList());

        return Pager.<HouseDto>builder()
                .currentPage(pageNum)
                .pageSize(pageSize)
                .result(dtos)
                .total(re.getTotal())
                .build();
    }

    @Override
    public House queryOneHouse(Long id) {
        return houseMapper.selectByHouseId(id);
    }

    /**
     * 添加房屋图片
     * 添加户型图片
     * 插入房产信息
     * 绑定用户到房产的关系
     *
     * @param user
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void addHouse(HouseDto houseDto, User user) throws IOException {
        House.HouseBuilder house = House.builder();
        List<String> imgs = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(houseDto.getHouseFiles())) {
            fileService.uploadObject2OSS(houseDto.getHouseFiles(), user.getEmail() + FileType.HOUSE_IMG_FILE.getTypeName());
            houseDto.getHouseFiles().forEach(x -> {
                String picUrl = "https://" + ossProperties.getBucketName() + "." + ossProperties.getENDPOINT() +
                        "/" + ossProperties.getAVATAR_FOLDER() + user.getEmail() + FileType.HOUSE_IMG_FILE.getTypeName() + x.getOriginalFilename();
                imgs.add(picUrl);
            });
            String images = Joiner.on(",").join(imgs);
            house.image(images);
            imgs.clear();
        }
        if (CollectionUtils.isNotEmpty(houseDto.getFloorPlanFiles())) {
            fileService.uploadObject2OSS(houseDto.getFloorPlanFiles(), user.getEmail() + FileType.HOUSE_IMG_FILE.getTypeName());
            houseDto.getFloorPlanFiles().forEach(x -> {
                String picUrl = "https://" + ossProperties.getBucketName() + "." + ossProperties.getENDPOINT() +
                        "/" + ossProperties.getAVATAR_FOLDER() + user.getEmail() + FileType.HOUSE_IMG_FILE.getTypeName() + x.getOriginalFilename();
                imgs.add(picUrl);
            });
            String images = Joiner.on(",").join(imgs);
            house.floorPlan(images);
            imgs.clear();
        }
        String properties = Joiner.on(",").join(houseDto.getFeatureList());
        Integer cityId = houseDto.getCityId();
        Integer cityCode = ProvinceEnum.getCityCode(ProvinceEnum.getProvinceEnum(cityId), houseDto.getTown());
        Long houseDtoId = getUUID();
        house.name(houseDto.getName())
                .id(houseDtoId)
                .type(houseDto.getType() == 1)
                .price(houseDto.getPrice())
                .area(houseDto.getArea())
                .baths(houseDto.getBaths())
                .beds(houseDto.getBeds())
                .remarks(houseDto.getRemarks())
                .properties(properties)
                .createTime(new Date())
                //省级code
                .cityId(houseDto.getCityId())
                //市级code
                .communityId(cityCode)
                .address(houseDto.getAddress())
                .rating(0D)
                .state(true);
        houseMapper.insert(house.build());
        bindUser2House(Long.valueOf(houseDtoId), user.getId(), false);
    }

    @Override
    public void bindUser2House(Long houseDtoId, Long userId, boolean collect) {
        HouseUser existhouseUser = houseUserService.selectHouseUser(userId, houseDtoId, collect ? HouseUserType.BOOKMARK.getType() : HouseUserType.SALE.getType());
        if (existhouseUser != null) {
            return;
        }
        HouseUser houseUser = HouseUser.builder()
                .houseId(houseDtoId)
                .userId(userId)
                .type(collect)
                .createTime(new Date())
                .build();
        houseUserService.insertHouseUser(houseUser);
    }

    @Override
    public void unbindUser2House(Long id, Long userId, HouseUserType type) {
        if (type.equals(HouseUserType.SALE)) {
            House house = House.builder().id(id).state(false).build();
            recommendService.remove(id);
            houseMapper.updateByPrimaryKeySelective(house);
        } else {
            houseUserService.deleteHouseUser(id, userId, type.getType());
        }

    }

    @Override
    public Pager<HouseDto> queryHouse(HouseDto query, Integer pageNum, Integer pageSize) {
        PageInfo<House> re = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> houseMapper.selectPageHouses(query));
        List<House> list = re.getList();
        List<HouseDto> dtos = list.stream().map(x -> HouseDto.builder()
                .id(x.getId())
                .address(x.getAddress())
                .area(x.getArea())
                .baths(x.getBaths())
                .beds(x.getBeds())
                .firstImg(x.getImage().split(",")[0])
                .name(x.getName())
                .price(x.getPrice())
                .typeStr(x.getType() ? "直售" : "出租")
                .remarks(x.getRemarks())
                .createTime(x.getCreateTime())
                .state(x.getState() ? 1 : 0)
                .build()).collect(Collectors.toList());

        return Pager.<HouseDto>builder()
                .currentPage(pageNum)
                .pageSize(pageSize)
                .result(dtos)
                .total(re.getTotal())
                .build();
    }

    @Override
    public void addUserMsg(UserMsg userMsg) throws IOException, MessagingException {
        houseMsgMapper.insertUserMsg(userMsg);
        User agent = userService.selectAgencyUserByUserId(userMsg.getUserId(), true);
        Map<String, String> params = Maps.newHashMap();
        params.put("email", userMsg.getEmail());
        params.put("msg", userMsg.getMsg());
        noticService.sendConsultNotice(NoticeType.LEAVING_MSG_NOTICE.getMessage(), userMsg.getUserName(), agent.getEmail(), params);
    }

    @Override
    public void updateRating(Long id, Double rating) {
        House house = queryOneHouse(id);
        Double oldRating = house.getRating();
        Double newRating = oldRating.equals(0D) ? rating : Math.min((oldRating + rating) / 2, 5);
        House updateHouse = new House();
        updateHouse.setId(id);
        updateHouse.setRating(newRating);
        houseMapper.updateByPrimaryKeySelective(updateHouse);
    }

    private Long getUUID() {
        long epochSecond = Instant.now().getEpochSecond();
        StringBuffer stringBuffer = new StringBuffer(String.valueOf(epochSecond));
        for (int i = 0; i < 4; i++) {
            Long aLong = Long.valueOf((int) (Math.random() * (10)));
            stringBuffer.append(aLong);
        }
        String reverse = stringBuffer.reverse().toString();
        String substring = reverse.substring(0, 8);
        return Long.valueOf(substring);
    }
}
