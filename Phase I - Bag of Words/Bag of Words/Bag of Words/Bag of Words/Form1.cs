using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;
using System.Text.RegularExpressions;

namespace Bag_of_Words
{
    public partial class Form1 : Form
    {

        List<String> finalwords = new List<string>();
        List<string> codes = new List<string>();


        public Form1()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            ///////////////////////////////////////////////////////
            //khundan az file asli
            string filePath = "D:\\test.java";
            StreamReader streamReader = new StreamReader(filePath); //get the file
            string stringWithMultipleSpaces = streamReader.ReadToEnd(); //load file to string
            streamReader.Close();

            stringWithMultipleSpaces = stringWithMultipleSpaces.Replace("(", " ");
            stringWithMultipleSpaces = stringWithMultipleSpaces.Replace(")", " ");
            stringWithMultipleSpaces = stringWithMultipleSpaces.Replace("+", " ");
            stringWithMultipleSpaces = stringWithMultipleSpaces.Replace(",", " ");
            stringWithMultipleSpaces = stringWithMultipleSpaces.Replace(";", " ");
            stringWithMultipleSpaces = stringWithMultipleSpaces.Replace("_", " ");
            stringWithMultipleSpaces = stringWithMultipleSpaces.Replace("=", " ");
            stringWithMultipleSpaces = stringWithMultipleSpaces.Replace("{", " ");
            stringWithMultipleSpaces = stringWithMultipleSpaces.Replace("}", " ");
            stringWithMultipleSpaces = stringWithMultipleSpaces.Replace(".", " ");
            //stringWithMultipleSpaces = stringWithMultipleSpaces.Replace("\r", " ");
            //stringWithMultipleSpaces = stringWithMultipleSpaces.Replace(System.Environment.NewLine, " ");

            //string regex = "(\\[.*\\])|(\".*\")|('.*')|(\\(.*\\))";
              string regex = "(\\[.*\\])|(\" \")|('.*')|(\\(.*\\))";
            string output = Regex.Replace(stringWithMultipleSpaces, regex, "");
            output = Regex.Replace(output, @"\t|\n|\r", "");

            output = output.Replace("\"", " ");

            Regex r = new Regex(" "); //specify delimiter (spaces)
            string[] words = r.Split(output); //(convert string to array of words)
            List<string> pulishedwords = new List<string>();
            foreach (string s in words)
            {
                if (s.Equals("") || s.Equals("+") )
                {
                    continue;
                }
                pulishedwords.Add(s);
            }
            pulishedwords.Remove(null);
            pulishedwords.Remove("\n");

            /////////////////////////////////////////////////
            //khundane file keyword 
            string filePath1 = "D:\\keywords.txt";
            StreamReader streamReader1 = new StreamReader(filePath1); //get the file
            string stringWithMultipleSpaces1 = streamReader1.ReadToEnd(); //load file to string
            streamReader1.Close();
            string[] words1 = stringWithMultipleSpaces1.Split(' ');

            ///////////////////////////////////////////////
            ////moqayese 2 list va hazfe anasori ke dar lit 2  bashe

          
            foreach(string s in pulishedwords)
            {
                finalwords.Add(s);
            }
            foreach( string s1 in pulishedwords)
            {
                foreach (string s2 in words1)
                {
                    if (s1.ToLower().Equals(s2.ToLower()))
                    {
                        finalwords.Remove(s1);
                        break;
                    }
                }

            }

        }

        private void button2_Click(object sender, EventArgs e)
        {
            DirectoryInfo d = new DirectoryInfo(@"D:\Th\projects_code\ant");//Assuming Test is your Folder
            FileInfo[] Files = d.GetFiles("*.java"); //Getting Text files
          
      

            foreach (FileInfo file in Files)
            {
                var parts = file.ToString().Split(new[] { '.' }).ToList();
                codes.Add(parts[0]);
            }
        }

        private void button3_Click(object sender, EventArgs e)
        {
            label1.Text = label2.Text = null;
            //string A = "strcmp";
            //string B = "string";
           
            
            foreach( string A in finalwords)
            {
                foreach(string B in codes)
                {
                    double JaccardIndex = (2 * ((double)CommonCharacters(A, B)) /
                                 ((double)(A.Count() + B.Count())));
                    if ( JaccardIndex == 1)
                    {
                        listBox1.Items.Add(B);
                    }

                }

            }
          
            //label1.Text = "Common: " + CharsofCommonChars(A, B).ToString();
            //label2.Text = "Jaccard Sim: " + JaccardIndex.ToString();


        }

        public int CommonCharacters(string s1, string s2)
        {
            bool[] matchedFlag = new bool[s2.Length];
            //List<string> commonchars = new List<string>();

            for (int i1 = 0; i1 < s1.Length; i1++)
            {
                for (int i2 = 0; i2 < s2.Length; i2++)
                {
                    if (!matchedFlag[i2] && s1.ToCharArray()[i1] == s2.ToCharArray()[i2])
                    {
                        //MessageBox.Show(s1.ToCharArray()[i1].ToString());
                        //commonchars.Add(s1.ToCharArray()[i1].ToString());
                        matchedFlag[i2] = true;
                        break;
                    }
                }
            }

            return matchedFlag.Count(u => u);
        }

        public List<char> CharsofCommonChars(string s1, string s2)
        {


            bool[] matchedFlag = new bool[s2.Length];
            List<char> commonchars = new List<char>();
            //List<string> finalchars = new List<string>();

            for (int i1 = 0; i1 < s1.Length; i1++)
            {
                for (int i2 = 0; i2 < s2.Length; i2++)
                {
                    if (!matchedFlag[i2] && s1.ToCharArray()[i1] == s2.ToCharArray()[i2])
                    {
                        //MessageBox.Show(s1.ToCharArray()[i1].ToString());
                        commonchars.Add(s1.ToCharArray()[i1]);
                        //MessageBox.Show(s1.ToCharArray()[i1].ToString());
                        matchedFlag[i2] = true;
                        break;
                    }
                }
            }
            return commonchars;
        }

        private void button4_Click(object sender, EventArgs e)
        {
            label1.Text = label2.Text = null;
            //string A = "strcmp";
            //string B = "string";
            string A = textBox1.Text;
            string B = textBox2.Text;
            double JaccardIndex = (2 * ((double)CommonCharacters(A, B)) /
                                   ((double)(A.Count() + B.Count())));

            label1.Text = "Common: " + CharsofCommonChars(A, B).ToString();
            label2.Text = "Jaccard Sim: " + JaccardIndex.ToString();



        }
    }
}
