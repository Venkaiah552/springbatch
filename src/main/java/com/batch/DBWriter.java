package com.batch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.User;
import com.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DBWriter implements ItemWriter<User> {

    private final UserRepository userRepository;

    ObjectMapper objectMapper ;

    @Value("${batchouput.file.location}")
    private String outputFileLocation;


    @Override
    public void write(List<? extends User> users) throws Exception {
        System.out.println("Data Saved for Users: " + users);
        //userRepository.saveAll(users);
        objectMapper = new ObjectMapper();

        FileOutputStream file = new FileOutputStream(outputFileLocation, true);
        objectMapper.writeValue(file, users);

       /* users.forEach(user -> {
            try {
                objectMapper.writeValue(file, user);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });*/

    }
}