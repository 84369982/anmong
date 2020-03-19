package com.anmong.core.web.wap;

import com.anmong.common.message.DosserReturnBody;
import com.anmong.common.message.DosserReturnBodyBuilder;
import com.anmong.core.dto.wap.friendapply.WapFriendApplyCreateDTO;
import com.anmong.core.dto.wap.friendapply.WapFriendApplyHandleDTO;
import com.anmong.core.service.wap.WapFriendApplyService;
import com.anmong.core.vo.wap.friendapply.WapFriendApplyIndexVO;
import com.anmong.core.vo.wap.friendapply.WapFriendIndexVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author songwenlong
 * 2018/3/12
 */
@RestController
@RequestMapping("wap/friend-apply")
public class WapFriendApplyController {

    @Autowired
    private WapFriendApplyService wapFriendApplyService;

    @PostMapping("add")
    @ApiOperation(value = "添加好友申请",tags = "wap-好友申请")
    public DosserReturnBody add(@Valid @RequestBody WapFriendApplyCreateDTO dto){
        wapFriendApplyService.add(dto);
        return new DosserReturnBodyBuilder().statusOk().build();
    }

    @GetMapping("find-all")
    @ApiOperation(value = "查看待验证的好友申请列表", tags = "wap-好友申请",response = WapFriendApplyIndexVO.class)
    public DosserReturnBody list(@ApiParam(value = "用户id") @RequestParam String id){
        return new DosserReturnBodyBuilder().collection(wapFriendApplyService.findFriendApplyList(id)).build();

    }

    @PostMapping("handle")
    @ApiOperation(value = "处理好友申请",tags = "wap-好友申请")
    public DosserReturnBody handle(@Valid @RequestBody WapFriendApplyHandleDTO dto){
        wapFriendApplyService.handle(dto);
        return new DosserReturnBodyBuilder().statusOk().build();
    }

    @GetMapping("find-all-friend")
    @ApiOperation(value = "查看好友列表", tags = "wap-好友申请",response = WapFriendIndexVO.class)
    public DosserReturnBody friendList(@ApiParam(value = "用户id") @RequestParam String id){
        return new DosserReturnBodyBuilder().collection(wapFriendApplyService.findFriendList(id)).build();

    }

}
