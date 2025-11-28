//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_HANDLE_METAL_HPP
#define STC_HANDLE_METAL_HPP

#define CALL(function) ASSERT(function != null, "Metal", #function)
#define null nullptr

#define ASSERT_HANDLE(HANDLE) ASSERT(handle, "Metal", "Failed to create " #HANDLE)

#endif //STC_HANDLE_METAL_HPP