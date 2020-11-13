package com.omg.document.controller;


import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.google.gson.Gson;
import com.omg.cmn.Message;
import com.omg.document.domain.DocumentVO;
import com.omg.document.service.DocumentService;




@Controller
public class DocumentController {
	final Logger  LOG = LoggerFactory.getLogger(DocumentController.class);
	
	@Autowired
	DocumentService documentService; 
	
	@Autowired
	MessageSource messageSource;
	
	String url = "http://localhost:8080/cmn";
	//--view--
	
	//--문서 목록 page
	@RequestMapping(value="document/document.do", method = RequestMethod.GET )
	public String document_view(DocumentVO documentVO, Model  model){
		
		
		documentVO.setEmployeeId("ID01");
		
		int flag = documentService.doempIdcheck(documentVO);
		model.addAttribute("flag", flag);
		LOG.debug("=doempIdcheck="+flag);
		
		if(flag == 0) {
			LOG.debug("등록되어진 문서가 없습니다.");
		}else{
			List<DocumentVO> IdSeleteList = documentService.doempIdSelectList(documentVO);
			model.addAttribute("IdSeleteList", IdSeleteList);
			model.addAttribute("IdSeleteSize", IdSeleteList.size());
			
			LOG.debug("=IdSeleteList="+IdSeleteList);
			LOG.debug("=IdSeleteSize="+IdSeleteList.size());
		}
		
		LOG.debug("종료");
		return  "document/document";
	}
	
	//--문서 등록page
	@RequestMapping(value="document/document_reg.do", method = RequestMethod.GET )
	public String document_reg(){
		LOG.debug("===========================");
		LOG.debug("=document_reg.do=");
		LOG.debug("===========================");
		return "document/document_reg";
	}
	
	//--문서 상세 page
	@RequestMapping(value="document/document_info.do" , method = RequestMethod.GET)
	public String document_info(  DocumentVO documentVO, Model  model){
		
		
		DocumentVO SeleteOne = documentService.doSelectOne(documentVO);
		LOG.debug("test");
		
		model.addAttribute("SeleteOne", SeleteOne);
		LOG.debug("SeleteOne"+SeleteOne);
		
		
		return "document/document_info";
	}
	
	
	
	//--기능----------------------------------------------------------------------------- 
	
	//--문서 단건 검색
	@RequestMapping(value="document/doSelectOne.do",method = RequestMethod.GET ,produces = "application/json;charset=UTF-8")
	@ResponseBody	
	public String doSelectOne(DocumentVO documentVO) {
        LOG.debug("==================");
        LOG.debug("=documentVO="+documentVO);
        LOG.debug("==================");		
        
        //documentVO.setDocumentId("E_0001");
        DocumentVO outVO =documentService.doSelectOne(documentVO);
        
        LOG.debug("==================");
        LOG.debug("=doSelectOne="+outVO);
        LOG.debug("==================");
        
        Gson gson=new Gson();
        String json = gson.toJson(outVO);
        LOG.debug("==================");
        LOG.debug("=json="+json);
        LOG.debug("==================");
         
        return json;
	}
	//-- 문서 전체 검색
	@RequestMapping(value="document/doSelectList.do",method = RequestMethod.GET ,produces = "application/json;charset=UTF-8")
	@ResponseBody	
	public String doSelectList(DocumentVO documentVO) {
        LOG.debug("==================");
        LOG.debug("=documentVO="+documentVO);
        LOG.debug("==================");		
        
        //documentVO.setDocumentId("E_0001");
        List<DocumentVO> outVO =documentService.doSelectList();
        
        LOG.debug("==================");
        LOG.debug("=doSelectList="+outVO);
        LOG.debug("==================");
        
        Gson gson=new Gson();
        String json = gson.toJson(outVO);
        LOG.debug("==================");
        LOG.debug("=json="+json);
        LOG.debug("==================");
         
        return json;
	}
	//-- 문서 사원 ID 기준 검색
	@RequestMapping(value="document/doempIdSelectList.do",method = RequestMethod.GET ,produces = "application/json;charset=UTF-8")
	@ResponseBody	
	public String doempIdSelectList(DocumentVO documentVO) {
        LOG.debug("==================");
        LOG.debug("=documentVO="+documentVO);
        LOG.debug("==================");		
        
     
        
        //documentVO.setDocumentId("E_0001");
        List<DocumentVO> outVO =documentService.doempIdSelectList(documentVO);
        
        LOG.debug("==================");
        LOG.debug("=doempIdSelectList="+outVO);
        LOG.debug("==================");
        
        Gson gson=new Gson();
        String json = gson.toJson(outVO);
        LOG.debug("==================");
        LOG.debug("=json="+json);
        LOG.debug("==================");
         
        return json;
	}
	
	
	
	//-- 삽입
	@RequestMapping(value="document/doInsert.do",method = RequestMethod.GET ,produces = "application/json;charset=UTF-8")
	@ResponseBody	
	public String doInsert(DocumentVO documentVO) {
        LOG.debug("==================");
        LOG.debug("=documentVO="+documentVO);
        LOG.debug("==================");		
        
        
        int row = documentService.doSeleteAllCount(documentVO);
        documentVO.setDocumentId(row+1+"");
        documentVO.setDocumentSet(0);
        documentVO.setEmployeeId("ID_NEW");
        
        
        
        //documentVO.setDocumentId("E_0001");
        int flag =documentService.doInsert(documentVO);
        
        LOG.debug("=doInsert="+flag);
        Message  message=new Message();
        message.setMsgId(flag+"");
        
        if(flag ==1 ) {
        	message.setMsgContents(documentVO.getTitle()+"문서가 등록 되었습니다.");
        }else {
        	message.setMsgContents(documentVO.getTitle()+"문서가 등록 실패 하였습니다.");
        }
        
        Gson gson=new Gson();
        String json = gson.toJson(message);
        LOG.debug("==================");
        LOG.debug("=json="+json);
        LOG.debug("==================");         
        
        return json;
	}
	
	//-- 삭제
	@RequestMapping(value="document/doDelete.do",method = RequestMethod.GET ,produces = "application/json;charset=UTF-8")
	@ResponseBody	
	public String doDelete(DocumentVO documentVO) {
        LOG.debug("==================");
        LOG.debug("=documentVO="+documentVO);
        LOG.debug("==================");		
        
        int flag =documentService.doDelete(documentVO);
        
        LOG.debug("=doDelete="+flag);
        Message  message=new Message();
        message.setMsgId(flag+"");
        
        if(flag ==1 ) {
        	message.setMsgContents(documentVO.getTitle()+"문서 삭제 성공하였습니다.");
        }else {
        	message.setMsgContents(documentVO.getTitle()+"문서 삭제 실패 하였습니다.");
        }
        
        Gson gson=new Gson();
        String json = gson.toJson(message);
        LOG.debug("==================");
        LOG.debug("=json="+json);
        LOG.debug("==================");         
        
        return json;
	}
	
	//-- 수정 
	@RequestMapping(value="document/doUpdate.do",method = RequestMethod.GET ,produces = "application/json;charset=UTF-8")
	@ResponseBody	
	public String doUpdate(DocumentVO documentVO) {
        LOG.debug("==================");
        LOG.debug("=documentVO="+documentVO);
        LOG.debug("==================");		
        
        //documentVO.setDocumentId("E_0001");
        int flag =documentService.doUpdate(documentVO);
        
        LOG.debug("=doUpdate="+flag);
        Message  message=new Message();
        message.setMsgId(flag+"");
        
        if(flag ==1 ) {
        	message.setMsgContents(documentVO.getTitle()+"문서 수정 하였습니다.");
        }else {
        	message.setMsgContents(documentVO.getTitle()+"문서 수정 실패 하였습니다.");
        }
        
        Gson gson=new Gson();
        String json = gson.toJson(message);
        LOG.debug("==================");
        LOG.debug("=json="+json);
        LOG.debug("==================");         
        
        return json;
	}



	
}
	

