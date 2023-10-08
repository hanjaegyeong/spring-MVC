package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>(); //static 사용
    private static long sequence = 0L; //static 사용

    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save: member={}", member);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }
    
    public Optional<Member> findByLoginId(String loginId){ // NULL 리턴될 수 있을 때: Optional 사용
/*        List<Member> all = findAll();
        for (Member m : all) {
            if (m.getLoginId().equals(loginId)){
                return Optional.of(m);
            }
        }
        return Optional.empty();*/

        // 위 코드 간단하게
        return findAll().stream() //리스트를 스트림으로 변환
                .filter(m -> m.getLoginId().equals(loginId)) // filter로 해당하는 값만 거르기:
                .findFirst(); //찾은 첫 번째 값만 반환
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
