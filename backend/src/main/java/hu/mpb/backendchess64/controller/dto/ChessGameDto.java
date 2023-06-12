package hu.mpb.backendchess64.controller.dto;

import hu.mpb.backendchess64.model.ChessResult;

import java.util.List;

public record ChessGameDto(ChessResult result, List<String> fenPositions,  List<String> nextLegalFens) {
}
