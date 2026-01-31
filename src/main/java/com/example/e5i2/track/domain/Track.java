package com.example.e5i2.track.domain;

import java.time.LocalTime;

import com.example.e5i2.user.domain.GenderType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tracks")
public class Track {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "distance", nullable = false)
	private Double distance;

	@Column(name = "track_time", nullable = false)
	private LocalTime trackTime;

	@Enumerated(EnumType.STRING)
	@Column(name = "track_type", nullable = false)
	private TrackType trackType;

	@Builder(access = AccessLevel.PRIVATE)
	private Track(
		Long userId,
		Double distance,
		LocalTime trackTime,
		TrackType trackType
	) {
		this.userId = userId;
		this.distance = distance;
		this.trackTime = trackTime;
		this.trackType = trackType;
	}

	public static Track createTrack(
		Long userId,
		Double distance,
		LocalTime trackTime,
		TrackType trackType
	) {
		return Track.builder()
			.userId(userId)
			.distance(distance)
			.trackTime(trackTime)
			.trackType(trackType)
			.build();
	}
}
