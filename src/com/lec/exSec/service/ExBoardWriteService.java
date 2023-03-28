package com.lec.exSec.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lec.exSec.dao.ExBoardDao;
import com.lec.exSec.dto.AdminDto;
import com.lec.exSec.dto.ExBoardDto;
import com.lec.exSec.dto.MemberDto;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class ExBoardWriteService implements Service {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String path = request.getRealPath("exBoardUp");
		int maxSize = 1024 * 1024 * 5; // 업로드 용량 5MB
		String bphoto = "";
		MultipartRequest mRequest = null;
		try {
			mRequest = new MultipartRequest(request, path, maxSize, "utf-8", 
							new DefaultFileRenamePolicy());
			Enumeration<String> params = mRequest.getFileNames();
			String param = params.nextElement();
			bphoto = mRequest.getFilesystemName(param);
			HttpSession httpSession = request.getSession();
			MemberDto member = (MemberDto)httpSession.getAttribute("member");	// id가져오기 위한 member
			AdminDto admin = (AdminDto)httpSession.getAttribute("admin");	// id가져오기 위한 admin
			if(member != null) {
				int bnum = 0;
				String mid = "";
				if(member != null) {
					mid = member.getMid();
				}
				String aid = "";
				if(admin != null) {
					aid = admin.getAid();
				}
				String btitle = mRequest.getParameter("btitle");
				String bcontent = mRequest.getParameter("bcontent");
				Timestamp bdate = new Timestamp(System.currentTimeMillis());
				String bip = request.getRemoteAddr();
				String writer = "";
				if(member.getMname() != null) {
					writer = member.getMname();
				}else if(admin.getAname() != null) {
					writer = admin.getAname();
				}
				ExBoardDao exDao = ExBoardDao.getInstance();
				ExBoardDto exDto = new ExBoardDto(bnum, mid, aid, btitle, bcontent, bphoto, bdate, 0, 0, 0, 0, bip, writer);
				int result = exDao.writeExBoard(exDto);
				if(result == ExBoardDao.SUCCESS) {
					request.setAttribute("exBoardResult", "글쓰기 성공");
				}else {
					request.setAttribute("exBoardError", "글쓰기 실패");
				}
			}else {
				request.setAttribute("exBoardResult", "로그인 해주세요");
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}	
		// 서버에 업로드된 파일을 소스 폴더로 복사
		File serverFile = new File(path + "/" + bphoto);
		if(serverFile.exists()) {
			InputStream is = null;
			OutputStream os = null;
			try {
				is = new FileInputStream(serverFile);
				os = new FileOutputStream("C:/exSecretary_Project/exSecretary/WebContent/exBoardUp/"+bphoto);
				byte[] bs = new byte[(int) serverFile.length()];
				while(true) {
					int readByteCnt = is.read(bs);
					if(readByteCnt==-1) break;
					os.write(bs, 0, readByteCnt);
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}finally {
				try {
					if(os!=null) os.close();
					if(is!=null) is.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			} // try-catch-finally
		} // if - serverFile
	}
}
