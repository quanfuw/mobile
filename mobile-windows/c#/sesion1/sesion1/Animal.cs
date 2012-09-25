using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace sesion1
{
    class Animal : IAnimal
    {

        private DateTime dob;

        public DateTime Dob
        {
            get { return dob; }
            set { dob = value; }
        }

        private int weight;

        public int Weight
        {
            get { return weight; }
            set { weight = value; }
        }
        
        private string name;

        public string Name
        {
          get { return name; }
          set { name = value; }
        }


        public virtual void input()
        {

           
            Console.Write("Name: ");
            Name = Console.ReadLine();
            Console.Write("Weight: ");
            Weight = Convert.ToInt16(Console.ReadLine());

        }

        public virtual void display()
        {
            Console.WriteLine("My name: {0} \nI am weight: {1}", Name, Weight);
            
        }


        public void watingToExit()
        {
            Console.Write("Enter to exit!");
            Console.ReadLine();
        }

        public virtual int caculateMyAge() {
            return DateTime.Now.Year - Dob.Year;
        }
    }
}
