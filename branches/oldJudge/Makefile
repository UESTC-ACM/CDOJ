LD=g++
CXX=g++
INCLUDE=include/
LOG_OFF=-DLOG_OFF
CXXFLAGS=-I $(INCLUDE) -g -Wall -O2 -m32 ${LOG_OFF}
LDFLAGS= -m32 ${LOG_OFF}
TARGET=bin/pyloncore
OBJECTS=objs/judge_all.o
all: $(TARGET)
$(TARGET): $(OBJECTS)
	$(LD) $(OBJECTS) -o $@ $(LDFLAGS)
	sudo chown root:root $(TARGET)
	sudo cp $(TARGET) /usr/local/bin/
	sudo chmod 4755 /usr/local/bin/pyloncore

objs/%.o: src/%.cc include/*.h
	$(CXX) $< -c -o $@ $(CXXFLAGS)

clean:
	make -C objs/ clean
	make -C bin/ clean
