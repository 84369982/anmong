package com.anmong.core.web.wap;

import com.anmong.common.fastsql.dto.ResultPage;
import com.anmong.common.message.DosserReturnBody;
import com.anmong.common.message.DosserReturnBodyBuilder;
import com.anmong.core.dto.wap.article.WapArticleCreateDTO;
import com.anmong.core.dto.wap.article.WapBlogIndexDTO;
import com.anmong.core.dto.wap.article.WapMyBlogIndexDTO;
import com.anmong.core.service.wap.WapArticleService;
import com.anmong.core.vo.wap.article.WapBlogIndexVO;
import com.anmong.core.vo.wap.article.WapBlogShowVO;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author songwenlong
 * 2018/2/27
 */
@RestController
@RequestMapping("wap/article")
public class WapArticleController {

    @Autowired
    private WapArticleService wapArticleService;

    @PostMapping("add")
    @ApiOperation(value = "发布微博", tags = "wap-微博")
    public DosserReturnBody changeUserInfo(@RequestBody @Valid WapArticleCreateDTO dto) {
        wapArticleService.add(dto);
        return new DosserReturnBodyBuilder().statusOk().build();
    }

    @GetMapping("find-all")
    @ApiOperation(value = "获取微博列表", tags = "wap-微博",response = WapBlogIndexVO.class)
    public DosserReturnBody list(@Valid WapBlogIndexDTO dto){
        ResultPage<WapBlogIndexVO> resultPage = wapArticleService.findAllBlog(dto);
        return new DosserReturnBodyBuilder()
                .paginate(dto.getPageNO(),dto.getPageSize(),resultPage.getTotalElements())
                .collection(resultPage.getContent()).build();
    }

    @GetMapping("find-one")
    @ApiOperation(value = "微博详情", tags = "wap-微博",response = WapBlogShowVO.class)
    public DosserReturnBody list(@ApiParam(value = "微博id") @RequestParam String id){
        return new DosserReturnBodyBuilder().collectionItem(wapArticleService.findOneBlog(id)).build();

    }

    @GetMapping("find-by-user-id")
    @ApiOperation(value = "根据用户id查询微博列表", tags = "wap-微博",response = WapBlogIndexVO.class)
    public DosserReturnBody findAllByUserId(@Valid WapMyBlogIndexDTO dto){
        ResultPage<WapBlogIndexVO> resultPage = wapArticleService.findAllByUserId(dto);
        return new DosserReturnBodyBuilder()
                .paginate(dto.getPageNO(),dto.getPageSize(),resultPage.getTotalElements())
                .collection(resultPage.getContent()).build();
    }
}
