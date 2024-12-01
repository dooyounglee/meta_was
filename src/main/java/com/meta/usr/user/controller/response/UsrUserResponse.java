package com.meta.usr.user.controller.response;

import java.util.List;

import com.meta.usr.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsrUserResponse {
    private Long usrNo;
    private String usrId;
    private String usrNm;
    private String useYn;
    private String usrComp;
    private String usrPos;
    private String usrPhone;
    private String usrPhoto;
    private List<String> roles;

    public static UsrUserResponse from(User user) {
        return UsrUserResponse.builder()
            .usrNo(user.getUsrNo())
            .usrId(user.getUsrId())
            .usrNm(user.getUsrNm())
            .useYn(user.getUseYn())
            .usrComp(user.getUsrComp())
            .usrPos(user.getUsrPos())
            .usrPhone(user.getUsrPhone())
            .usrPhoto(user.getUsrPhoto())
            .build();
    }
}
