package com.ezen.myProject.handler;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ezen.myProject.domain.FileVO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Slf4j
@AllArgsConstructor
@Component
public class FileHandler {

	private final String UP_DIR = "D:\\_javaweb\\_java\\fileUpload";
	
	public List<FileVO> uploadFiles(MultipartFile[] files){
		//날짜별로 파일을 관리하기 위해
		LocalDate date = LocalDate.now();
		log.info("date >> "+date);
		String today = date.toString();//오늘 날짜를 string으로 변환 => 2022-12-07 => 파일 구분자로 변경 => 2022\12\07
		today = today.replace("-", File.separator);
		
		File folders = new File(UP_DIR, today);//up_dir 폴더 하위로 today 폴더를 생성
		
		//폴더가 있으면 생성x, 없으면 생성o
		if(!folders.exists()) {
			folders.mkdirs(); //생성//실제 폴더에 대한 구성		
		}
		//파일 경로설정 완료
		List<FileVO> fList = new ArrayList<FileVO>();
		for(MultipartFile file : files){
			FileVO fvo = new FileVO();
			fvo.setSave_dir(today);//파일경로설정
			fvo.setFile_size(file.getSize());//사이즈설정
			
			String originalFileName = file.getOriginalFilename();//경로를 포함할 수도 있는 파일명
			log.info("originalfileName>> "+originalFileName);
			
			String onlyFileName = originalFileName
					.substring(originalFileName.lastIndexOf("\\")+1);//실제 파일명만 추출
			log.info("only fileName>> "+onlyFileName);			
			fvo.setFile_name(onlyFileName);//파일 이름 설정
			
			UUID uuid = UUID.randomUUID();
			fvo.setUuid(uuid.toString());//uuid 설정
			//여기까지 fvo에 저장할 파일을 정보 생성 -> set
			
			//디스크에 저장할 파일 객체 생성
			String fullFileName= uuid.toString()+"_"+onlyFileName;//실제 파일 저장명
			File storeFile = new File(folders, fullFileName);//저장할 파일(위에서 지정한 폴더 경로로 fullfilename 저장)
			
			//io에 대한 exception 발생
			try {
				file.transferTo(storeFile); //원본 객체를 저장을 위한 형태로 복사
				if(isImageFile(storeFile)) {
					fvo.setFile_type(1);
					File thumbNail = new File(folders,uuid.toString()+"_th_"+onlyFileName);
					Thumbnails.of(storeFile).size(75, 75).toFile(thumbNail);
				}
			} catch (Exception e) {
				log.info(">>>File 생성 오류~!!");
				e.printStackTrace();
			}
			fList.add(fvo);
			
		}
		return fList;
	}
	
	//이미지 파일인지 아닌지를 체크
	private boolean isImageFile(File storeFile) throws IOException {
		String mimeType = new Tika().detect(storeFile);//해당하는 파일이 어떤 형태인지 나타내줌//image/png, image/jpg...
		return mimeType.startsWith("image")?true:false;
	}
}
