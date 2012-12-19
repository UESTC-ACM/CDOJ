##############################################
#        Judge core for UESTC ACMICPC        #
#         by fish(lyhypacm@gmail.com)        #
##############################################

DIR=$(shell pwd)
BIN_DIR=$(DIR)/bin
LIB_DIR=$(DIR)/lib
SRC_DIR=$(DIR)/src
INCLUDE_DIR=$(DIR)/include
OBJ_DIR=$(DIR)/obj
DEPS_DIR=$(DIR)/deps
PROGRAM=$(BIN_DIR)/judgecore

EXTENSION=cc
OBJS=$(patsubst $(SRC_DIR)/%.$(EXTENSION), $(OBJ_DIR)/%.o,$(wildcard $(SRC_DIR)/*.$(EXTENSION)))
DEPS=$(patsubst $(OBJ_DIR)/%.o, $(DEPS_DIR)/%.d, $(OBJS))

INCLUDE=\
		-I$(INCLUDE_DIR)

CXX=g++
CXXLD=g++
CFLAGS=-Wall -W -g -O2
LDFLAGS=

.PHONY: all clean rebuild

all:$(OBJS)
	@echo [CXXLD] $(DEPS_DIR)
	@$(CXXLD) -o $(PROGRAM) $(OBJS) $(LDFLAGS)

$(DEPS_DIR)/%.d: $(SRC_DIR)/%.$(EXTENSION)
	@echo [DEP] $@
	@$(CXX) -MM $(INCLUDE) $(CFLAGS) $< | sed -e 1's,^,$(OBJ_DIR)/,' > $@

sinclude $(DEPS)

$(OBJ_DIR)/%.o: $(SRC_DIR)/%.$(EXTENSION)
	@echo [CXX] $@
	@$(CXX) $< -o $@ -c $(CFLAGS) $(INCLUDE)

rebuild: clean all

clean:
	@echo clean project...
	@rm -rf $(OBJS) $(LIB_DIR)/lib* $(BIN_DIR)/*

