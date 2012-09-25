using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace sesion1
{
    class Cat : Animal
    {
        private string color;
        
        public string Color { 
            set {color = value;}
            get { return color; }
        }

        public override void input() {
            
            base.input();
            Console.WriteLine("Color: ");
            Color = Console.ReadLine();
        }

        public override void display() {
            base.display();
            Console.WriteLine("My Color: {0}", Color);
        }

        public override int caculateMyAge()
        {
            Console.WriteLine(base.Dob);
            return 0;
        }
    }
}
