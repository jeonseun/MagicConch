package team.univ.magic_conch.utils.file;

import org.springframework.beans.factory.annotation.Value;

public interface StorageService {


    /**
     * 파일 저장
     *
     * @param bytes    파일 바이트 스트림
     * @param filename 파일 명
     * @param location 파일 저장 경로
     * @return 저장된 파일에 접근 가능한 경로 정보
     */
    String save(byte[] bytes, String filename, String location);
}
