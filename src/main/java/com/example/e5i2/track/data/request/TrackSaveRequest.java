package com.example.e5i2.track.data.request;

import java.time.LocalTime;

import com.example.e5i2.track.domain.TrackType;

import jakarta.validation.constraints.NotNull;

public record TrackSaveRequest(
	@NotNull Double distance,
	@NotNull LocalTime trackTime,
	@NotNull TrackType trackType
) {
}
