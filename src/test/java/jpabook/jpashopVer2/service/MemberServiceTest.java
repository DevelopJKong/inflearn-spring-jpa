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

    @Test
    public void duplicateCheck() throws Exception {
        // * given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        // * when
        memberService.join(member1);
        try {
            memberService.join(member2);
        } catch (IllegalStateException e) {
            System.out.println(e);
            return;
        }

        // * then
        Assert.fail("예외 발생");

    }
}
