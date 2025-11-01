//
// Created by cheerwizard on 01.11.25.
//

#ifndef STC_FILEBRIDGE_HPP
#define STC_FILEBRIDGE_HPP

namespace stc {

    struct FileBridge {
        static std::string loadFile(const char* filepath);
        static std::vector<u32> loadBinaryFile(const char* filepath);

    private:
        static constexpr auto TAG = "FileBridge";
    };

}

#endif //STC_FILEBRIDGE_HPP