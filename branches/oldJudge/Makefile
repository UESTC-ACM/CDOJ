LD=g++
CXX=g++
INCLUDE=include/
CXXFLAGS=-I $(INCLUDE) -g -Wall -O2
LDFLAGS=
TARGET=bin/pyloncore
OBJECTS=objs/judge_all.o
all: $(TARGET)
$(TARGET): $(OBJECTS)
	$(LD) $(OBJECTS) -o $@ $(LDFLAGS)
	sudo chown root:root $(TARGET)
	sudo chmod 4755 $(TARGET)
#sudo cp pyloncore ~/pylon/work/fish/

objs/%.o: src/%.cc include/*.h
	$(CXX) $< -c -o $@ $(CXXFLAGS)

clean:
	make -C objs/ clean
	make -C bin/ clean

