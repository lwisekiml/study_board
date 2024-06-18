package study.board.board.dto;

import lombok.*;
import study.board.board.Board;
import study.board.comment.dto.BoardCommentDto;
import study.board.file.UploadFileDto;
import study.board.file.UploadFilesDto;
import study.board.member.MemberDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto { // 게시글 조회

    private Long id;

    private String loginId;
    private String title;
    private String content;
    private int views;
    private Set<MemberDto> recommend;

    private UploadFileDto attachFile;
    private List<UploadFilesDto> imageFiles = new ArrayList<>();

    private List<BoardCommentDto> comments = new ArrayList<>();

    public static BoardDto toBoardDto(Board entity) {
        return new BoardDto(
                entity.getId(),
                entity.getLoginId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getViews(),
                entity.getRecommend().stream().map(MemberDto::toMemberDto).collect(Collectors.toSet()),
                UploadFileDto.toUploadFileDto(entity.getAttachFile()),
                entity.getImageFiles().stream().map(UploadFilesDto::toUploadFilesDto).collect(Collectors.toList()),
                entity.getComments().stream().map(BoardCommentDto::toBoardCommentDto).collect(Collectors.toList())
        );
    }
}
