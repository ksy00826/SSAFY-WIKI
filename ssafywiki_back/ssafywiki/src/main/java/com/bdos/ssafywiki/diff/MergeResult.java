package com.bdos.ssafywiki.diff;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MergeResult {
    private boolean success;
    String message;
    String conflictDetail;
}
