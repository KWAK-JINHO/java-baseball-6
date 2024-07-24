package baseball;

import camp.nextstep.edu.missionutils.Randoms;
import camp.nextstep.edu.missionutils.Console;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        // 게임 시작문구 출력
        System.out.println("숫자 야구 게임을 시작합니다.");

        // 컴퓨터의 랜덤 숫자 생성
        List<Integer> computer = new ArrayList<>();
        while (computer.size() < 3) {
            int randomNumber = Randoms.pickNumberInRange(1, 9);
            if (!computer.contains(randomNumber)) {
                computer.add(randomNumber);
            }
        }
        // System.out.println("컴퓨터가 생성한 숫자: " + computer);

        // 사용자에게 서로다른 3자리 수 입력 받기
        System.out.print("숫자를 입력해주세요 : ");
        String readInput = Console.readLine();
        //System.out.println(readInput);
    }
}
