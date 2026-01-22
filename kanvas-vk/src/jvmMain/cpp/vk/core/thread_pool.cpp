//
// Created by cheerwizard on 06.07.25.
//

#include "thread_pool.hpp"

vk::ThreadPool::ThreadPool(u32 size) {
    for (int i = 0; i < size; i++) {
        std::thread thread([&] { runLoop(); });
        thread.detach();
    }
}

vk::ThreadPool::~ThreadPool() {
    running = false;
}

void vk::ThreadPool::submit(const Task &task) {
    taskQueue.push(task);
}

void vk::ThreadPool::runLoop() {
    running = true;
    while (running) {
        Task task;
        taskQueue.pop(task);
        task.task();
    }
}