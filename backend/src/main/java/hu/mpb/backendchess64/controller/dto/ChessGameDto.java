package hu.mpb.backendchess64.controller.dto;

import hu.mpb.backendchess64.model.ChessResult;

public record ChessGameDto(ChessResult result, String[] fenPositions, String[] nextLegalFens) {
}
