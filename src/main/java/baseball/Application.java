package baseball;

import camp.nextstep.edu.missionutils.Randoms;
import camp.nextstep.edu.missionutils.Console;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Application {
    public static void main(String[] args) {
        while (true) {
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
            System.out.println("컴퓨터가 생성한 숫자: " + computer);

            boolean gameRunning = true;
            while (gameRunning) {
                // 사용자에게 서로다른 3자리 수 입력 받기
                System.out.print("숫자를 입력해주세요 : ");
                String readInput = Console.readLine();
                //System.out.println(readInput);
                //System.out.println(readInput.getClass().getName());

                // 사용자에게 받은 입력값 유효성 검사
                try {
                    validateInput(readInput);
                } catch (IllegalArgumentException e) {
                    System.out.println("잘못된 입력: " + e.getMessage());
                    continue; // 잘못된 입력일 경우 다시 입력 받기
                }

                // 힌트 출력 기능
                String[] parts = readInput.split(""); // 입력값을 개별적으로 처리하기 위해서 split 으로 분리
                // 1. 스트라이크 출력 기능
                int strikes = 0;
                for (int i = 0; i < 3; i++) {
                    int userNumber = Integer.parseInt(parts[i]); // 사용자 입력값에서 숫자 가져오기
                    if (computer.get(i) == userNumber) {
                        strikes++;
                    }
                }
                // 스트라이크 결과 출력
                System.out.println(strikes + " 스트라이크");

                // 2. 볼 출력 기능
                int balls = 0;
                for (int i = 0; i < 3; i++) {
                    int userNumber = Integer.parseInt(parts[i]);
                    if (computer.contains(userNumber) && computer.get(i) != userNumber) {
                        balls++;
                    }
                }
                // 볼 결과 출력
                System.out.println(balls + " 볼");

                // 3. 낫싱 출력 기능
                if (strikes == 0 && balls == 0) {
                    System.out.println("낫싱");
                }

                // 4. 3스트라이크 일때, 게임 종료 문구 출력
                if (strikes == 3) {
                    System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임 종료");
                    gameRunning = false; // 게임 종료
                }
            }
            // 게임 재시작 또는 종료 여부 선택
            System.out.print("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
            String choice = Console.readLine();

            if (choice.equals("2")) {
                System.out.println("게임을 종료합니다.");
                break; // 프로그램 종료
            }
            // 재시작할 경우 아무 작업도 하지 않고 루프 다시 시작.
        }
    }

    private static void validateInput(String input) {
        // 1. 숫자가 1~9까지의 숫자인지 확인
        for (char c : input.toCharArray()) {
            if (c < '1' || c > '9') {
                throw new IllegalArgumentException("입력값은 1~9 사이의 숫자만 포함되어야 합니다.");
            }
        }

        // 2. 3자리 숫자인지 확인 (중복된 코드로 사용할 필요 없음)
        if (input.length() != 3) {
            throw new IllegalArgumentException("입력값은 3자리 숫자여야 합니다.");
        }

        // 3. 같은 숫자가 중복된 경우 확인
        Set<Character> uniqueDigits = new HashSet<>();
        for (char c : input.toCharArray()) {
            if (!uniqueDigits.add(c)) {
                throw new IllegalArgumentException("입력값에는 중복된 숫자가 포함될 수 없습니다.");
            }
        }
    }
}