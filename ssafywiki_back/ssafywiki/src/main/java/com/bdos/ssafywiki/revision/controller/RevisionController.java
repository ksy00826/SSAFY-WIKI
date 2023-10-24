package com.bdos.ssafywiki.revision.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "버전 관리 API")
@RestController
public class RevisionController {

    @Operation(summary = "버전 비교", description = "두개의 버전을 비교합니다.")
    @GetMapping
    public ResponseEntity diff(){

        return null;
    }
}
