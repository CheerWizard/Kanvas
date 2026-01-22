//
// Created by cheerwizard on 04.07.25.
//

#ifndef POINTERS_HPP
#define POINTERS_HPP

#include "memory.hpp"

// Scope - basically same as std::unique_ptr<T>, during it's lifetime it calls New()/Delete() on T resource.
// you also can't copy or move it anywhere else, so it's unique to one place.
namespace vk {

    template<typename T>
    struct Scope : Ptr<T> {

        Scope() = default;

        template<typename... Args>
        Scope(Args&&... args);

        ~Scope();

        // can't copy
        Scope(const Scope&) = delete;
        Scope& operator=(const Scope&) = delete;

        // can move
        Scope(Scope&&) noexcept = default;
        Scope& operator=(Scope&&) noexcept = default;
    };

    template<typename T>
    template<typename ... Args>
    Scope<T>::Scope(Args &&... args) {
        this->New(std::forward<Args>(args)...);
    }

    template<typename T>
    Scope<T>::~Scope() {
        this->Delete();
    }

}

#endif //POINTERS_HPP
