package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Modifying
    @Query("UPDATE Message m SET m.messageText = :mText, m.timePostedEpoch = :timePosted WHERE m.messageId = :messageId")
    int updateMessageByID(@Param("mText") String mText, @Param("timePosted") Long timePosted, @Param("messageId") Integer messageId);

    @Modifying
    @Query("DELETE FROM Message m WHERE m.messageId = :messageId")
    int deleteMessageByID(@Param("messageId") Integer messageId);

    @Modifying
    @Query("DELETE FROM Message m WHERE m.messageId = :messageId")
    int deleteByMessageId(@Param("messageId") Integer messageId);

    List<Message> findByPostedBy(Integer postedBy);


}
