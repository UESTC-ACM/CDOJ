#include <cstdio>
#include <cstring>
#include <string>
#include <iostream>
#include <vector>
using namespace std;

char buf[1<<16];

string Error(string message)
{
	cout << "Error in Gao : " << message << endl;
	return "<span class=\"label label-important\">"+message+"</span>";
}

string Gao(string file)
{
	if (file == "")
		return Error("Empty filename !");

	string filename = file+".html";
	FILE *pfile = fopen(filename.c_str(),"r");
	if (pfile == NULL)
		return Error("Can not open "+filename+"!");

	string content = "";
	while (fgets(buf,1<<16,pfile) != NULL)
		content = content+buf;
	fclose(pfile);

	/* phrase */
	/* #include(xxx) */

	string sflag = "#include(";
	string eflag = ")";
	string target;
	int spos,epos;
	while (true)
	{
		spos = content.find(sflag);
		if (spos == content.npos)
			break;

		epos = content.find(eflag,spos);
		if (epos == content.npos)
			break;

		target = content.substr(spos+sflag.size(),epos-spos-sflag.size()-eflag.size()+1);

		content.replace(spos,epos-spos+1,Gao(target));
	}

	return content;
}

string Gen(string file)
{
	if (file == "")
		return "Empty filename !";

	string filename = "../"+file+".html";
	FILE *pfile = fopen(filename.c_str(),"w");
	if (pfile == NULL)
		return "Can not open "+filename+"!";

	fprintf(pfile,"%s",Gao(file).c_str());

	fclose(pfile);

	return "Success build "+filename+"!";
}

int main()
{
	FILE *pfile = fopen("work.txt","r");
	if (pfile == NULL)
	{
		puts("Can not open work.txt!");
		return 0;
	}
	string filename;
	while (fscanf(pfile,"%s",buf) != EOF)
	{
		filename = buf;
		cout << Gen(filename) << endl;
	}

	fclose(pfile);
	return 0;
}
