package com.bdos.ssafywiki.diff;

import com.bdos.ssafywiki.exception.ExceptionCode;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MergeDto {
    private String result;
    private ExceptionCode exceptionCode;
}
