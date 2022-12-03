package jpabook.jpashopVer2.service;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashopVer2.domain.Member;
import jpabook.jpashopVer2.repository.MemberRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    public void join() throws Exception {
        // * given
        Member member = new Member();
        member.setName("kim");

        // * when
        Long savedId = memberService.join(member);

        // * then
        Assert.assertEquals(member, memberRepository.findOne(savedId));
    }

}
