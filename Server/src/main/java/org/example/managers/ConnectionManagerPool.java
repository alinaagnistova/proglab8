package org.example.managers;

import org.example.dtp.Response;

import java.io.ObjectOutputStream;

/**
 * Рекорд для хранения в коллекции всех пулов для проверки выполнения
 * @param response
 * @param objectOutputStream
 */
public record ConnectionManagerPool(Response response, ObjectOutputStream objectOutputStream){};

