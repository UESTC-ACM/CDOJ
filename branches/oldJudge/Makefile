LD=g++
CXX=g++
INCLUDE=include/
CXXFLAGS=-I $(INCLUDE) -g -Wall -O2 #-D LOG_OFF
LDFLAGS= #-D LOG_OFF
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
install:
	sudo cp $(TARGET) ../../trunk/src/main/webapp/WEB-INF/work/fish/
	sudo cp $(TARGET) ../../trunk/src/main/webapp/WEB-INF/work/mzry1992/

