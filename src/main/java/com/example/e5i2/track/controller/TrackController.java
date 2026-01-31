package com.example.e5i2.track.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.e5i2.track.data.request.TrackSaveRequest;
import com.example.e5i2.track.data.response.TrackSaveResponse;
import com.example.e5i2.track.service.TrackService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/track")
public class TrackController {
	private final TrackService trackService;

	@PostMapping("/{userId}")
	public ResponseEntity<TrackSaveResponse> trackSave(
		@PathVariable("userId") Long userId,
		@Valid @RequestBody TrackSaveRequest trackSaveRequest
	) {
		TrackSaveResponse trackSaveResponse = trackService.trackSave(userId, trackSaveRequest);
		return ResponseEntity.status(HttpStatus.OK).body(trackSaveResponse);
	}
}
