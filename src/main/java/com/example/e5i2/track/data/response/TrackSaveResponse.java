package com.example.e5i2.track.data.response;

import java.time.LocalTime;

import com.example.e5i2.track.domain.TrackType;

import lombok.Builder;

@Builder
public record TrackSaveResponse(
	Long userId,
	Double distance,
	LocalTime trackTime,
	TrackType trackType
) {
}
