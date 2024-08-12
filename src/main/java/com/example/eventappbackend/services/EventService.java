package com.example.eventappbackend.services;

import com.example.eventappbackend.DTO.EventDTO;
import com.example.eventappbackend.Repositories.EventRepository;
import com.example.eventappbackend.Repositories.SubscriptionRepository;
import com.example.eventappbackend.entities.Event;
import com.example.eventappbackend.entities.EventCategory;
import com.example.eventappbackend.entities.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service

public class EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public Event addEvent(Event event) {
        return eventRepository.save(event);
    }

    public Optional<Event> getEvent(Long id) {
        return eventRepository.findById(id);
    }

    public Page<Event> getAllEvents(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    public List<Event> getEventsByCategory(EventCategory eventCategory) {
        return eventRepository.findByCategory(eventCategory);
    }
    public Event updateEvent(Event event) {
        return eventRepository.save(event);
    }


    public String uploadPhoto(MultipartFile photo) throws IOException {
        String fileName = "photo_" + System.currentTimeMillis() + ".jpg";
        String uploadDir = "uploaded-photos/";
        File uploadFile = new File(uploadDir + fileName);

        // Log file path for debugging
        System.out.println("Uploading file to: " + uploadFile.getAbsolutePath());

        // Create the directory if it doesn't exist
        uploadFile.getParentFile().mkdirs();

        // Save the file
        try (FileOutputStream fos = new FileOutputStream(uploadFile)) {
            fos.write(photo.getBytes());
        }

        return uploadDir + fileName;
    }

    public Subscription subscribe(Long eventId, String email) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with ID: " + eventId));

        Subscription subscription = new Subscription();
        subscription.setEvent(event);
        subscription.setEmail(email);

        return subscriptionRepository.save(subscription);
    }


}
