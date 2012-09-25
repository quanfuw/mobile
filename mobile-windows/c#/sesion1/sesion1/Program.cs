using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace sesion1
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("=== Animal ===");
            IAnimal animal = new Animal();
            animal.input();
            animal.display();
            animal.watingToExit();

            Console.WriteLine("=== Cat ===");
            IAnimal cat = new Cat();
            cat.input();
            cat.display();
            cat.watingToExit();
            cat.caculateMyAge();

            Console.WriteLine("=== Chicken ===");
            IAnimal chicken = new Chicken();
            chicken.input();
            chicken.display();
            chicken.caculateMyAge();
            chicken.watingToExit();

        }

    }
}
