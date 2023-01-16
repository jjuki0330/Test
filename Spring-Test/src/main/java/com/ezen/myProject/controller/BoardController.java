package com.ezen.myProject.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ezen.myProject.domain.BoardDTO;
import com.ezen.myProject.domain.BoardVO;
import com.ezen.myProject.domain.FileVO;
import com.ezen.myProject.domain.PagingVO;
import com.ezen.myProject.domain.UserVO;
import com.ezen.myProject.handler.FileHandler;
import com.ezen.myProject.handler.PagingHandler;
import com.ezen.myProject.repository.UserDAO;
import com.ezen.myProject.service.BoardService;
import com.ezen.myProject.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/board/*")
@Controller
public class BoardController {

	@Inject
	private BoardService bsv;
	@Inject
	private UserDAO userDao;
	@Inject
	private UserService usv;
	
	@Inject
	private FileHandler fhd;
	
	@GetMapping("/register")
	public String boardRegisterGet() {
		return "/board/register";
	}
	
//	쿼리스트링으로 뭐 달고 오는 경우
//	@PostMapping("/register")
//	public String register(BoardVO bvo, RedirectAttributes reAttr, @RequestParam(name="bnNo")int bNo) {
//				
//		return"";
//	
//	}
	//수정 정보를 반영해서 들고 가는 경우는 redirect 처리
	//redirect로 리턴하면 model 대신 redirectattributes를 사용한다.
	//insert, update, delete => redirect 처리
	//list, detail, modify => 일반적으로 처리
	//포워드 처리, redirect 처리
	
	@PostMapping("/register")
	public String register(BoardVO bvo, RedirectAttributes reAttr,
			@RequestParam(name="files",required = false)MultipartFile[] files) {//required: 만약에 files에 값이 없어도 에러를 내지 마라
		log.info("bvo register>> "+bvo.toString());
		log.info("files>> "+files);
		
		//dto에서 만든 fList??
		List<FileVO> fList = null;
		if(files[0].getSize()>0) {//files에 값이 있다
			fList = fhd.uploadFiles(files);//실제 파일들에 대한 정보를 담은 fList 생성			
			BoardDTO bdto = new BoardDTO(bvo, fList);
			int isOk = bsv.registerFile(bdto);
			reAttr.addFlashAttribute("msg", isOk>0?"1":"0");
			log.info("board register>>"+ (isOk>0?"ok":"fail"));
			return"redirect:/board/list";
		}else {
			log.info("file null");
			int isOk = bsv.register(bvo);
			reAttr.addFlashAttribute("msg", isOk>0?"1":"0");
			log.info("board register>>"+ (isOk>0?"ok":"fail"));
			return"redirect:/board/list";
		}
//		int isOk = bsv.register(new BoardDTO(bvo,fList));
//		int isOk = bsv.register(bvo);
	} 
	
	@GetMapping("/list")
	public void list(Model model,PagingVO pvo) {
		List<BoardVO> list = bsv.getList(pvo);
		int totalCount = bsv.getListSize(pvo);
		PagingHandler pgh = new PagingHandler(pvo, totalCount);
		model.addAttribute("pgh", pgh);
		model.addAttribute("list", list);
	}
	
	@GetMapping({"/detail","/modify"})
	public void detail(Model model, @RequestParam("bno")int bno) {
//		BoardVO bvo = bsv.getDetail(bno);
		BoardDTO bdto = bsv.getDetailFile(bno);
		log.info("detail bvo>> "+bdto.getBvo().toString());
		//log.info("detail fList>> "+bdto.getFList().get(0).toString());
		model.addAttribute("bvo", bdto.getBvo());
		model.addAttribute("fList", bdto.getFList());
	}
	@PostMapping("/modify")
	public String modify(RedirectAttributes reAttr, BoardVO bvo, @RequestParam(name="files",required = false)MultipartFile[] files) {
		log.info("modify>>>"+bvo.toString());
		UserVO user = userDao.getUser(bvo.getWriter());//사용하는 유저가 맞는지..?
		int isOk = bsv.modify(bvo, user);
		if(isOk>0) {
			List<FileVO> fList = null;
			if(files[0].getSize()>0) {//files에 값이 있다
				fList = fhd.uploadFiles(files);//실제 파일들에 대한 정보를 담은 fList 생성			
				BoardDTO bdto = new BoardDTO(bvo, fList);
				int isUp = bsv.modifyFile(bdto);
				log.info("board modifyFile>>"+ (isUp>0?"ok":"fail"));
			}else {
				log.info("file null");
			}
		}		
		log.info(">>> modify : "+(isOk>0?"ok":"fail"));
		reAttr.addFlashAttribute(isOk>0?"1":"0");		
		return "redirect:/board/list";
	}
	
	@GetMapping("/remove")
	public String remove(@RequestParam("bno")int bno, RedirectAttributes reAttr, HttpServletRequest request) {
		log.info("delete check 1");
//		1. HttpSession ses=request.getSession();
//		UserVO user=(UserVO)ses.getAttribute("ses");
//		log.info(user.toString());
//		2. UserVO user = userDao.getUsers((UserVO)request.getSession().getAttribute("ses"));
		UserVO user = usv.getUser(request);
		
		int isOk = bsv.getDelete(bno, user);
		log.info(">>> delete: "+(isOk>0?"ok":"fail"));
		reAttr.addFlashAttribute(isOk>0?"1":"0");//alert창 띄울지 안띄울지 예비해서 보내는거 없어도 됨
		return "redirect:/board/list";
	}
	
	@DeleteMapping(value="/{uuid}", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> delete(@PathVariable("uuid")String uuid){
		log.info(" file delete uuid>> "+uuid);
		int isUp = bsv.delete(uuid);
		log.info("delete isUp>> "+isUp);
		return isUp>0?
				new ResponseEntity<String>("1", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
