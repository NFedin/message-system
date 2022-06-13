package com.nikfedin.messagesystem.repository;

import com.nikfedin.messagesystem.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByReceiver(String id);

    List<Message> findByReceiverAndUnread(String id, Boolean unread);
}
