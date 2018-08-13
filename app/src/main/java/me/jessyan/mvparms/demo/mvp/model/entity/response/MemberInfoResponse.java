package me.jessyan.mvparms.demo.mvp.model.entity.response;

import me.jessyan.mvparms.demo.mvp.model.entity.member_info.City;
import me.jessyan.mvparms.demo.mvp.model.entity.member_info.County;
import me.jessyan.mvparms.demo.mvp.model.entity.member_info.MemberBean;
import me.jessyan.mvparms.demo.mvp.model.entity.member_info.Province;
import me.jessyan.mvparms.demo.mvp.model.entity.member_info.Rank;

public class MemberInfoResponse extends BaseResponse {
    private MemberBean member;

    @Override
    public String toString() {
        return "MemberInfoResponse{" +
                "member=" + member +
                '}';
    }

    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
        this.member = member;
    }
}
