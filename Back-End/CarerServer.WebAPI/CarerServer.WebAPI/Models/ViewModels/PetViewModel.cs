using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CarerServer.WebAPI.Models.ViewModels
{
    public class PetViewModel
    {
        private Guid id;
        private string name;
        private string breed;
        private string kind;
        private string ownerId;

        public Guid Id { get => id; set => id = value; }
        public string Name { get => name; set => name = value; }
        public string Kind { get => kind; set => kind = value; }
        public string Breed { get => breed; set => breed = value; }
        public string OwnerId { get => ownerId; set => ownerId = value; }
    }

}
