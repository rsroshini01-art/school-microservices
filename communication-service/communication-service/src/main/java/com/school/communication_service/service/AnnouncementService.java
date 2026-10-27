package com.school.communication_service.service;

import com.school.communication_service.dto.AnnouncementRequest;
import com.school.communication_service.dto.AnnouncementResponse;
import com.school.communication_service.entity.Announcement;
import com.school.communication_service.entity.TargetAudience;
import com.school.communication_service.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    // The class lives in academic-service, so only its id is stored here.
    // Checking that the class id exists will be added in Step 6 (OpenFeign).

    public AnnouncementResponse create(AnnouncementRequest request) {
        if (request.getTargetAudience() == TargetAudience.CLASS && request.getClassId() == null) {
            throw new RuntimeException("Class ID is required when target audience is CLASS");
        }

        Announcement announcement = new Announcement();
        announcement.setTitle(request.getTitle());
        announcement.setContent(request.getContent());
        announcement.setTargetAudience(request.getTargetAudience());
        announcement.setClassId(request.getClassId());
        announcement.setPublished(false);

        Announcement saved = announcementRepository.save(announcement);
        return AnnouncementResponse.fromEntity(saved);
    }

    public List<AnnouncementResponse> getAll() {
        return announcementRepository.findAll()
                .stream().map(AnnouncementResponse::fromEntity).collect(Collectors.toList());
    }

    public AnnouncementResponse getById(Long id) {
        Announcement a = announcementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Announcement not found with id: " + id));
        return AnnouncementResponse.fromEntity(a);
    }

    public AnnouncementResponse update(Long id, AnnouncementRequest request) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Announcement not found with id: " + id));

        announcement.setTitle(request.getTitle());
        announcement.setContent(request.getContent());
        announcement.setTargetAudience(request.getTargetAudience());

        Announcement updated = announcementRepository.save(announcement);
        return AnnouncementResponse.fromEntity(updated);
    }

    public AnnouncementResponse publish(Long id) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Announcement not found with id: " + id));
        announcement.setPublished(true);
        Announcement updated = announcementRepository.save(announcement);
        return AnnouncementResponse.fromEntity(updated);
    }

    public void delete(Long id) {
        if (!announcementRepository.existsById(id)) {
            throw new RuntimeException("Announcement not found with id: " + id);
        }
        announcementRepository.deleteById(id);
    }
}