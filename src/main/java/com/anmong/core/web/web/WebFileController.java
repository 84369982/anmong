package com.anmong.core.web.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anmong.common.message.CommonException;
import com.anmong.common.message.DosserReturnBody;
import com.anmong.common.message.DosserReturnBodyBuilder;
import com.anmong.common.pagination.DataTable;
import com.anmong.core.dto.web.file.WebFileIndexDTO;
import com.anmong.core.entity.File;
import com.anmong.core.enums.CommonEnum;
import com.anmong.core.service.web.WebFileService;
import com.google.common.collect.Lists;

@RestController
@RequestMapping("web/file")
public class WebFileController {
	
	@Autowired
	private WebFileService webFileService;
	
	@PostMapping("find-all")
	@ApiOperation(value = "查询文件列表", tags = "后台-文件")
	public DataTable findAll(HttpServletRequest request,@Valid WebFileIndexDTO indexDTO) {
		DataTable dt = webFileService.findAll(request,indexDTO);
		return dt;
	}
	
	/**
	 * 
	 * @param id
	 * @param state:这里用state来指代fileType字段
	 * @param request
	 * @return
	 */
	@PostMapping("change-state")
	@ApiOperation(value = "更改文件状态", tags = "后台-文件")
	public DosserReturnBody update(@RequestParam String id,@RequestParam Short state,HttpServletRequest request) {
		File entity = new File();
		entity.setId(id);
		entity.setType(state);
		entity.setState(CommonEnum.State.禁用.code.shortValue());
		webFileService.changeState(entity,request);
		return new DosserReturnBodyBuilder().statusOk().build();
	}
	
	@PostMapping("delete")
	@ApiOperation(value = "删除文件", tags = "后台-文件")
	public DosserReturnBody delete(@RequestParam String id) {
		File file = webFileService.findOne(id);
		if(file == null || StringUtils.isEmpty(file.getId())) {
			throw CommonException.businessException("文件不存在!");
		}
		webFileService.deleteBatch(Lists.newArrayList(file));
		return new DosserReturnBodyBuilder().statusOk().build();
	}

}
