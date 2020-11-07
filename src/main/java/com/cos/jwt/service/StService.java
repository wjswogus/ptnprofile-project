package com.cos.jwt.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cos.jwt.domain.pt.Pt;
import com.cos.jwt.domain.st.St;
import com.cos.jwt.domain.st.StRepository;
import com.cos.jwt.domain.user.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StService {
	
	private final StRepository stRepository;
	
	@Transactional
	public void stWrite(HttpServletRequest request, User trainer, String st_name, String st_content, String st_address, int st_price, MultipartFile st_img) {
		try {		
			
			String uploadFolder = getFolder();
			String uploadFolderPath = request.getServletContext().getRealPath("/pt-app/public/");
			
			File realUploadFolderPath = new File(uploadFolderPath, uploadFolder);
			if(!realUploadFolderPath.exists()) {
				realUploadFolderPath.mkdirs();
			}
			
			String filename = "";
			
			UUID uuid = UUID.randomUUID(); 
			String strSt_img = st_img.getOriginalFilename();
			String uploadFilename = uuid.toString() + "_" + strSt_img; 
			String realPath = realUploadFolderPath + "\\" + uploadFilename;
			System.out.println(realPath);
			st_img.transferTo(new File(realPath));	
			filename = "../" + uploadFolder + "/" + uploadFilename;
			St st = St.builder().user(trainer).st_name(st_name).st_content(st_content).st_address(st_address).st_price(st_price).st_img(filename).build();
			stRepository.save(st);

		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	private String getFolder() {
	    SimpleDateFormat uploadDate = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = new Date();
	    String str = uploadDate.format(date);
	    
	    return str.replace("-", File.separator);
	}
	
	
	@Transactional
	public Page<St> stList(Pageable pageable) {
		return stRepository.findAll(pageable);
	}
	
	@Transactional
	public St stDetail(int no) {
		return stRepository.findById(no).orElseThrow(() -> new IllegalArgumentException(no + "번의 게시물은 없습니다."));
	}
	
	@Transactional
	public int stRemove(User trainer, int no) {
		St st = stRepository.findById(no).orElseThrow(() -> new IllegalArgumentException(no + "번 스튜디오 게시물은 없습니다."));
		if(st.getUser().getId().equals(trainer.getId())) {
			stRepository.deleteById(no);
			return 1;
		} else {
			return 0;
		}
	}
	
	@Transactional
	public int stModify(User trainer, int no, String st_name, String st_content, int st_price, MultipartFile st_img) {
		St st = stRepository.findById(no).orElseThrow(() -> new IllegalArgumentException(no + "번 스튜디오 게시물은 없습니다."));
		
		if(st.getUser().getId().equals(trainer.getId())) {
			st.setSt_name(st_name);
			st.setSt_content(st_content);
			st.setSt_price(st_price);
			try {
				String path = "E:\\workspace\\moonjoowan\\ptnprofile-project\\src\\main\\webapp\\pt-app\\public\\";
				String old_st_img = path + st.getSt_img().substring(3);
				UUID uuid = UUID.randomUUID();
				String new_st_img = uuid.toString() + "_" + st_img.getOriginalFilename();
				
				String uploadFolder = getFolder();
				File folder = new File(path, uploadFolder);
				if(!folder.exists()) {
					folder.mkdirs();
				}
				
				st_img.transferTo(new File(folder + new_st_img));
				st.setSt_img("../" + folder + new_st_img);
				File old = new File(old_st_img);
				if(old.exists()) {
					if(old.delete()) {
						System.out.println("파일 삭제 성공!!");
					} else {
						System.out.println("파일 삭제 실패!!");
					}
				} else {
					System.out.println("파일이 존재하지 않습니다.");
				}
			} catch (Exception e) {
				e.getMessage();
			}
			return 1;
		} else {
			return 0;
		}
	}
} 
