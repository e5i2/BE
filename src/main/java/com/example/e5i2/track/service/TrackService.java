package com.example.e5i2.track.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.e5i2.track.data.request.TrackSaveRequest;
import com.example.e5i2.track.data.response.TrackSaveResponse;
import com.example.e5i2.track.domain.Track;
import com.example.e5i2.track.repository.TrackRepository;
import com.example.e5i2.user.domain.User;
import com.example.e5i2.user.exception.UserErrorCode;
import com.example.e5i2.user.exception.UserException;
import com.example.e5i2.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackService {
	private final UserRepository userRepository;
	private final TrackRepository trackRepository;

	@Transactional
	public TrackSaveResponse trackSave(Long userId, TrackSaveRequest trackSaveRequest) {
		User user = userRepository.findById(userId).orElseThrow(
			() -> new UserException(UserErrorCode.USER_NOT_FOUND)
		);

		Track track = trackRepository.save(
			Track.createTrack(
				user.getId(),
				trackSaveRequest.distance(),
				trackSaveRequest.trackTime(),
				trackSaveRequest.trackType()
			)
		);

		return TrackSaveResponse.builder()
			.userId(track.getUserId())
			.distance(track.getDistance())
			.trackTime(track.getTrackTime())
			.trackType(track.getTrackType())
			.build();
	}
}
