package ru.dsoccer1980.dao;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.dsoccer1980.model.Question;
import ru.dsoccer1980.util.exception.NotFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionDaoCSVImpl implements QuestionDao {

    @Value("${file.name}")
    private String FILENAME;

    @Override
    public List<Question> getAllQuestions() {
        ClassLoader classLoader = getClass().getClassLoader();
        List<Question> questions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(classLoader.getResource(FILENAME).getFile()));
             CSVReader csvReader = new CSVReader(reader)) {
            List<String[]> records = csvReader.readAll();
            for (String[] record : records) {
                questions.add(new Question(Integer.valueOf(record[0]), record[1], record[2]));
            }
        } catch (IOException e) {
            throw new NotFoundException(e.getMessage());
        }
        return questions;
    }

}
