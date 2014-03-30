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
#sudo cp pyloncore ~/pylon/work/fish/

objs/%.o: src/%.cc include/*.h
	$(CXX) $< -c -o $@ $(CXXFLAGS)

clean:
	make -C objs/ clean
	make -C bin/ clean
install:
	sudo cp $(TARGET) ../../trunk/src/main/webapp/WEB-INF/work/fish/
	sudo cp $(TARGET) ../../trunk/src/main/webapp/WEB-INF/work/mzry1992/
	sudo cp $(TARGET) ../../trunk/src/main/webapp/WEB-INF/work/gongbaoa/
	sudo cp $(TARGET) ../../trunk/src/main/webapp/WEB-INF/work/kennethsnow/
	sudo chmod 4755 ../../trunk/src/main/webapp/WEB-INF/work/fish/pyloncore
	sudo chmod 4755 ../../trunk/src/main/webapp/WEB-INF/work/mzry1992/pyloncore
	sudo chmod 4755 ../../trunk/src/main/webapp/WEB-INF/work/gongbaoa/pyloncore
	sudo chmod 4755 ../../trunk/src/main/webapp/WEB-INF/work/kennethsnow/pyloncore
