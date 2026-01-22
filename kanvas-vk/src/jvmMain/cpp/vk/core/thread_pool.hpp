//
// Created by cheerwizard on 06.07.25.
//

#ifndef THREAD_POOL_HPP
#define THREAD_POOL_HPP

#include <condition_variable>
#include <mutex>
#include <array>
#include <functional>
#include <thread>
#include "types.hpp"

template<typename T, size_t size>
struct ConcurrentQueue {

    void push(const T& data);
    void pop(T& data);
    bool isEmpty() const;
    bool isFull() const;
    size_t getSize() const { return size; }

private:
    u32 tail = 0;
    u32 head = 0;
    std::array<T, size> queue = {};
    std::mutex mutex;
    std::condition_variable notFull;
    std::condition_variable notEmpty;
};

template<typename T, size_t size>
void ConcurrentQueue<T, size>::push(const T &data) {
    std::unique_lock lock(mutex);
    notFull.wait(lock, [this]() { return !isFull(); });
    queue[head] = data;
    head = (head + 1) % size;
    notEmpty.notify_one();
}

template<typename T, size_t size>
void ConcurrentQueue<T, size>::pop(T& data) {
    std::unique_lock lock(mutex);
    notEmpty.wait(lock, [this]() { return !isEmpty(); });
    data = queue[tail];
    tail = (tail + 1) % size;
    notFull.notify_one();
}

template<typename T, size_t size>
bool ConcurrentQueue<T, size>::isEmpty() const {
    return tail == head;
}

template<typename T, size_t size>
bool ConcurrentQueue<T, size>::isFull() const {
    return (head + 1) % size == tail;
}

struct Task {
    std::function<void()> task = {};
    size_t scheduledTime = 0;
};

struct ThreadPool {

    ThreadPool(u32 size);
    ~ThreadPool();

    void submit(const Task& task);

private:
    void runLoop();

    bool running = false;
    ConcurrentQueue<Task, 100> taskQueue;
};

#endif //THREAD_POOL_HPP
