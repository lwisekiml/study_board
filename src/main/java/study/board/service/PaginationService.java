package study.board.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class PaginationService {

    private static final int BAR_LENGTH = 5;

    public List<Integer> getPaginationBarNumbers(int currentPageNumber, int totalPages) {

        int startNumber = Math.max(currentPageNumber - (BAR_LENGTH / 2), 0);
        // 마지막 페이지로 가도 BAR가 BAR_LENGTH 만큼 보이도록 하기 위함
        if (startNumber > totalPages - BAR_LENGTH) {
            startNumber = totalPages - BAR_LENGTH;
        }

        int endNumber = Math.min(startNumber + BAR_LENGTH, totalPages);

        return IntStream.range(startNumber, endNumber).boxed().toList(); // startNumber <= [range] < endNumber
    }

}
