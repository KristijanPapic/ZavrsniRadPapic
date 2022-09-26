using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Carer.Model.DomainModels
{
    public class PetDomainModel
    {
        private Guid id = Guid.NewGuid();
        private string name;
        private string breed = "";
        private string kind;
        private Guid ownerId;

        public Guid Id { get => id; set => id = value; }
        public string Name { get => name; set => name = value; }
        public string Kind { get => kind; set => kind = value; }
        public string Breed { get => breed; set => breed = value; }
        public Guid OwnerId { get => ownerId; set => ownerId = value; }
    }
}
