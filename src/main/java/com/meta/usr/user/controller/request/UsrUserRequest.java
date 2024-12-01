package com.meta.usr.user.controller.request;

import com.meta.cmm.dto.SearchDto;
import com.meta.usr.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

public class UsrUserRequest {
    
    @Getter
    @Setter
    @SuperBuilder
    public static class UsrUserSelect extends SearchDto {
        private Long usrNo;
        private String usrId; // 사용자ID
        private String usrNm; // 사용자명
        private String usrPw; // 사용자비번
        private String usrComp; // 수행사명
        private String usrPos; // 직위
        private String usrPhone; // 연락처
        private String useYn; // 사용여부

        public User toEntity(String encodedPw) {
            return User.builder()
                .usrId(usrId)
                .usrNm(usrNm)
                .usrPw(encodedPw)
                .usrComp(usrComp)
                .usrPos(usrPos)
                .usrPhone(usrPhone)
                .useYn(useYn)
                .build();
        }
    }

    @Data
    @Builder
    public static class UsrUserCreate {
        private String usrId; // 사용자ID
        private String usrNm; // 사용자명
        private String usrPw; // 사용자비번
        private String usrComp; // 수행사명
        private String usrPos; // 직위
        private String usrPhone; // 연락처
        private String useYn; // 사용여부

        public User toEntity(String encodedPw) {
            return User.builder()
                .usrId(usrId)
                .usrNm(usrNm)
                .usrPw(encodedPw)
                .usrComp(usrComp)
                .usrPos(usrPos)
                .usrPhone(usrPhone)
                .useYn(useYn)
                .build();
        }
    }

    @Data
    @Builder
    public static class UsrUserUpdate {
        private Long usrNo; // 사용자번호
        private String usrNm; // 사용자명
        private String usrPw; // 사용자비번
        private String usrComp; // 수행사명
        private String usrPos; // 직위
        private String usrPhone; // 연락처
        private String useYn; // 사용여부
    }

    @Data
    @Builder
    public static class UsrUserChangePw {
        private String usrPw; // 사용자비번
        private String newPw; // 새로운비번
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UsrUserPhoto {
        private String usrPhoto;
    }

    @Data
    @Builder
    public static class UsrUserReset {
        private long usrNo;
    }
}
