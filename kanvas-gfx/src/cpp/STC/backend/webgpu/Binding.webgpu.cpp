//
// Created by cheerwizard on 25.10.25.
//

#include "../Binding.hpp"

namespace stc {

    BindingLayout::BindingLayout(const Device &device, const std::vector<Binding> &bindings) : bindings(bindings) {
        New();
    }

    BindingLayout::~BindingLayout() {
        Delete();
    }

}